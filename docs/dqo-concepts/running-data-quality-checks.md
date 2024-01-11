# Running data quality checks

## Overview

DQOps supports running data quality checks from the DQOps command-line shell, user interface, Python client
and triggered directly by a call to a REST API endpoint [run_checks](../client/operations/jobs.md#run_checks).

Data quality checks can be also scheduled to run in regular intervals to continuously monitor the data sources.
DQOps uses an internal job scheduler that is based on a popular Quartz library. The schedules are defined as CRON
expressions compatible with the Linux CRON format.

Data quality checks can be queued for execution from the [DQOps command-line shell](command-line-interface.md)
by running the [check run](../command-line-interface/check.md#dqo-check-run) command.
It is the easiest way to understand how the data quality check targeting is used.

## Targeting data sources and tables
The data quality checks are configured on tables in the [.dqotable.yaml](../reference/yaml/TableYaml.md) table
specification files. The structure of a simplified *DQOps user home* folder with one data source *sales-dwh*
and one configured table *public.fact_sales* is shown below. We will run all data quality checks configured on that table.

``` { .asc .annotate hl_lines="5 7" }
$DQO_USER_HOME
├───.data                                                                   
│   └────...                                                  
└───sources                                                                
    └───sales-dwh(1)
        ├─connection.dqoconnection.yaml(2)
        └─public.fact_sales.dqotable.yaml(3)
```

1. The target data source defined as the nested folder name inside the *$DQO_USER_HOME/sources* folder.
2. The data source configuration file.
3. The configuration of the data quality checks for the *public.fact_sales* table. The target table is identified
   by the file name. The file extension [.dqotable.yaml](../reference/yaml/TableYaml.md)
   identifies a table data quality specification file.

This *DQOps user home* folder has one data source *sales-dwh* that is identified as a folder name inside the
*$DQO_USER_HOME/sources* folder. 
The *DQOps user home* stores the configuration of one table *public.fact_sales*. The configuration of the
data quality checks for this table is stored in the file with a [.dqotable.yaml](../reference/yaml/TableYaml.md) extension.
The name of the target table is not defined inside the *public.fact_sales.dqotable.yaml*. Instead, the target
table is identified by the file name, followed by the *.dqotable.yaml* file name extension.

The example below shows how to run data quality checks from the [DQOps command-line shell](command-line-interface.md)
by running the [check run](../command-line-interface/check.md#dqo-check-run) command.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh(1) --full-table-name=public.fact_sales(2)
```

1. The target data source name. Support patterns such as *name_prefix_\**.
2. The target table name provided as a *schema.table* name. The name supports patterns both in the
   schema name part and in the table name part. To run checks in all tables inside a schema, use *target_schema.\**.
   Other examples of supported patterns are: *schema_prefix_\*.target_table*, *\*.fact_\**.

The parameters passed to the [check run](../command-line-interface/check.md#dqo-check-run) command are:

| Parameter           | Description                                                                                                                                   | Example                             |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------|
| `--connection`      | Data source name, matches the folder name inside the *$DQO_USER_HOME/sources* folder.                                                         | --connection=sales-dwh              |
| `--full-table-name` | The target table name in the format: <schema_name>.<table_name>. This parameter supports patterns such as *target_schema.\** or *\*.fact_\**. | --full-table-name=public.fact_sales |


## Targeting multiple tables
The next example shows a *DQOps user home* with multiple tables, sharing the same table name prefix.

``` { .asc .annotate hl_lines="7-8" }
$DQO_USER_HOME
├───.data                                                                   
│   └────...                                                  
└───sources                                                                
    └───sales-dwh(1)
        ├─connection.dqoconnection.yaml
        ├─public.dim_product.dqotable.yaml(2)
        ├─public.dim_date.dqotable.yaml(3)
        └─public.fact_sales.dqotable.yaml
```

1. The target data source
2. The first table that matches the *public.dim_\** pattern
3. The second table that matches the *public.dim_\** pattern

Running all data quality checks for all tables in the *public* schema that are dimension tables (table names
beginning with a *dim_* prefix) is shown below.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.dim_*(1)
```

1. The target table name as a pattern that matches only the *public* schema and all tables whose names
   begin with a *dim_* name prefix.


## Selecting checks to run 
DQOps supports also running checks only on selected columns, running only one data quality check on multiple
tables or columns. We can also target one type of checks, *profiling*, *monitoring* or *partitioned*.

The [check run](../command-line-interface/check.md#dqo-check-run) command for running checks on a column,
running only one check or all checks from one type is shown below. 

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check=<check>(1) --column=<column>(2)
               --check-type=monitoring(3) --time-scale=daily(4) --category=nulls(5)
```

1.  The data quality check name, for example [daily_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-nulls-percent).
2.  The column name, supports also patters such as *column_name_prefix_\**, *\*_column_name_suffix* or *prefix\*suffix*.
3.  The data quality check type, supported values are *profiling*, *monitoring* and *partitioned*.
4.  The time scale for *monitoring* and *partitioned* checks. Supported values are *daily* and *monthly*.
5.  The category of checks to run.

A selection of the most important targeting filters is shown in the following table. The list of targeting filters is not limited
to the filters described below. Please consult the [check run command-line command](../command-line-interface/check.md#dqo-check-run)
to see the full list of supported targeting filters.

| Parameter      | Description                                                                                                        | Example                                                                                       |
|----------------|--------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| `--check`      | The check name. All data quality checks are described in the [Checks](../checks/index.md) section.              | --check=[daily_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-nulls-percent) |
| `--column`     | The target column name. This parameter supports patterns such as *\*_id*.                                          | --column=customer_id                                                                          |
| `--check-type` | The check type to run only checks of that type. Supported values are  *profiling*, *monitoring* and *partitioned*. | --check-type=monitoring                                                                       |
| `--time-scale` | The time scale for *monitoring* and *partitioned* checks. Supported values are *daily* and *monthly*.              | --time-scale=daily                                                                            |
| `--category`   | The name of the category of checks to run.                                                                         | --category=nulls                                                                              |


## Running table and column checks
The following *.dqotable.yaml* example shows a column on which we want to run all the data quality checks.

``` { .yaml .annotate linenums="1" hl_lines="12-28" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
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

When a column name is provided to filter the checks, only column-level checks are executed.
The table-level check are ignored. The `--column=` filter will select column(s) to analyze.
The filter supports also patterns such as `--column=*_id`, `--column=date_*`, or `--column=col_*_id`.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --column=cumulative_confirmed(1)
```

1.  The column name, supports patterns.


## Targeting checks by name
The following *.dqotable.yaml* example shows two configured data quality checks.

``` { .yaml .annotate linenums="1" hl_lines="8-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
```

In order to run only the [daily_row_count](../checks/table/volume/row-count.md#daily-row-count),
without running the [monthly_row_count](../checks/table/volume/row-count.md#monthly-row-count),
we can target it with a `--check=` parameter as shown in the following example.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check=daily_row_count(1)
```

1. The name of the data quality check to run. Other check names are listed in the [Checks](../checks/index.md) reference.


## Targeting checks by type
Instead of running checks one by one, we can run all checks from one type of checks.

### **Running profiling checks**
The next example *.dqotable.yaml* shows a mixture of *profiling* and *monitoring* checks. We want to run only *profiling* checks.

``` { .yaml .annotate linenums="1" hl_lines="5-9" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
```

The type of checks is filtered using the `--check-type=` parameter. In order to run only
[profiling checks](checks/profiling-checks/profiling-checks.md), we will use the following command.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=profiling(1)
```

1.  Selects the *profiling* checks to run.


### **Running monitoring checks**
Instead of *profiling* checks, we can run only **monitoring** checks. If the time scale is not configured
to select only *daily* or *monthly* checks, both types are run if they are configured.

The following example highlights the group of checks that we want to run.

``` { .yaml .annotate linenums="1" hl_lines="10-20" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
```

The following command line runs all *monitoring* checks.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=monitoring(1)
```

1. Run the *monitoring* checks only.


### **Running daily monitoring checks**
We can limit running the checks only to the *daily monitoring* checks, by providing both the `--check-type=` parameter
and the `--time-scale=` parameter that targets only *daily* or *monthly* checks. 

The following YAML example highlights the *daily monitoring* checks that we want to run.

``` { .yaml .annotate linenums="1" hl_lines="11-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
```
 
The `check run` command accepts a `--time-scale=daily` parameter that filters only by *daily* checks.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=monitoring --time-scale=daily(1)
```

1.  Selects the *daily* checks. Another supported value is the *monthly* checks.

### **Running partitioned checks**
Running [partitioned checks](checks/partition-checks/partition-checks.md) to analyze partitions is similar
to running *monitoring* checks. We can also select the time scale.

The following example highlights the *partitioned* checks that will be run.

``` { .yaml .annotate linenums="1" hl_lines="22-26" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
  partitioned_checks:
    daily:
      volume:
        daily_partition_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_partition_row_count:
          warning:
            min_count: 1
```

The daily partitioned checks are run with the following command.


``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=partitioned(1) --time-scale=daily
```

1. Selects the *partitioned* checks.

### **Run all daily checks**
DQOps supports any combination of targeting. For example, we can run only *daily* checks, which will
target both *monitoring* and *partitioned* checks. Because the *profiling* checks are not using a time scale, they will be excluded.

The following YAML example highlights the *daily* checks that will be run.

``` { .yaml .annotate linenums="1" hl_lines="11-15 22-26" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_row_count:
          warning:
            min_count: 1
  partitioned_checks:
    daily:
      volume:
        daily_partition_row_count:
          warning:
            min_count: 1
    monthly:
      volume:
        monthly_partition_row_count:
          warning:
            min_count: 1
```

The `check run` command will use only the `--time-scale=daily` parameter to run the *daily* checks.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --time-scale=daily(1)
```

1.  All types of *daily* checks are run.


### **Running table and column checks**
We will use the previous example that showed *daily monitoring* checks configured both at the table and column levels.

``` { .yaml .annotate linenums="1" hl_lines="6-10 17-28" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
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

The previous command that runs only *daily monitoring* checks will run both the table and column level checks.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=monitoring --time-scale=daily
```


## Targeting a category of checks
We can also run all checks in a category, skipping other categories. 

!!! info "Filtering by category and check type"

    Please be aware that the same categories are present also in other types of checks (*profiling*, *monitoring* or *partitioned*),
    so using just the category name may not be enough. The check name were unique in the previous example of targeting
    a single check by its name, but the check type and optionally the check time scale should be specified to avoid
    running unexpected data quality checks.

The following example shows both checks defined in the *nulls* and in the *schema* category on a column.
The highlighted section shows only the *schema* checks that we want to run on all columns.

``` { .yaml .annotate linenums="1" hl_lines="16-19 26-29" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 0
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
```

We will use the `--category=schema` filter to run the *schema* checks. Additionally, following the note above,
only *daily monitoring* checks will be run.

``` { .asc .annotate }
dqo> check run --connection=sales-dwh --full-table-name=public.fact_sales --check-type=monitoring --time-scale=daily
               --category=schema(1)
```

1.  The check category filter.


## What's next

- Learn how to [run checks from the user interface](../working-with-dqo/run-data-quality-checks.md).
- Read about how the [check results are stored](data-storage-of-data-quality-results.md).
- Learn how DQOps [executed the checks](architecture/data-quality-check-execution-flow.md).
