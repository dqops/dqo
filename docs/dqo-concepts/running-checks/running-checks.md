# Running data quality checks
DQOps supports running data quality checks from the DQOps command-line shell, user interface, Python client
and triggered directly by a call to a REST API endpoint [run_checks](../../client/operations/jobs.md#run_checks).

Data quality checks can be also scheduled to run in regular intervals to continuously monitor the data sources.
DQOps uses an internal job scheduler that is based on a popular Quartz library. The schedules are defined as CRON
expressions compatible with the Linux CRON format.

Data quality checks can be queued for execution from the [DQOps command-line shell](../command-line-interface/command-line-interface.md)
by running the [check run](../../command-line-interface/check.md#dqo-check-run) command.
It is the easiest way to understand how the data quality check targeting is used.

## Targeting data sources and tables
The data quality checks are configured on tables in the [.dqotable.yaml](../../reference/yaml/TableYaml.md) table
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
   by the file name. The file extension [.dqotable.yaml](../../reference/yaml/TableYaml.md)
   identifies a table data quality specification file.

This *DQOps user home* folder has one data source *sales-dwh* that is identified as a folder name inside the
*$DQO_USER_HOME/sources* folder. 
The *DQOps user home* stores the configuration of one table *public.fact_sales*. The configuration of the
data quality checks for this table is stored in the file with a [.dqotable.yaml](../../reference/yaml/TableYaml.md) extension.
The name of the target table is not defined inside the *public.fact_sales.dqotable.yaml*. Instead, the target
table is identified by the file name, followed by the *.dqotable.yaml* file name extension.

The example below shows how to run data quality checks from the [DQOps command-line shell](../command-line-interface/command-line-interface.md)
by running the [check run](../../command-line-interface/check.md#dqo-check-run) command.

``` { .asc .annotate }
dqo> check run -c=sales-dwh(1) -t=public.fact_sales(2)
```

1. The target data source name. Supports patterns such as *name_prefix_\**.
2. The target table name provided as a *schema.table* name. The name supports patterns both in the
   schema name part and in the table name part. To run checks in all tables inside a schema, use *target_schema.\**.
   Other examples of supported patterns are: *schema_prefix_\*.target_table*, *\*.fact_\**.

The parameters passed to the [check run](../../command-line-interface/check.md#dqo-check-run) command are:

| Parameter | Description                                                                                                                                   | Example      |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| -c        | Data source name, matches the folder name inside the *$DQO_USER_HOME/sources* folder.                                                         | -c=sales-dwh |
| -t        | The target table name in the format: <schema_name>.<table_name>. This parameter supports patterns such as *target_schema.\** or *\*.fact_\**. | -t=public.fact_sales |


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
dqo> check run -c=sales-dwh -t=public.dim_*(1)
```

1. The target table name as a pattern that matches only the *public* schema and all tables whose names
   begin with a *dim_* name prefix.


## Targeting columns and checks 
DQOps supports also running checks only on selected columns, running only one data quality check on multiple
tables or columns or running only one type of checks (*profiling*, *monitoring* or *partitioned*).

The [check run](../../command-line-interface/check.md#dqo-check-run) command for running checks on a column,
running only one check or all checks from one type is shown below.

``` { .asc .annotate }
dqo> check run -c=sales-dwh -t=public.fact_sales -ch=<check>(1) -col=<column>(2) -ct=monitoring(3) -ts=daily(4)
```

1. The data quality check name, for example [daily_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-nulls-percent).
2. The column name, supports also patters such as *column_name_prefix_\**, *\*_column_name_suffix* or *prefix\*suffix*.
3. The data quality check type, supported values are *profiling*, *monitoring* and *partitioned*.
4. The time scale for *monitoring* and *partitioned* checks. Supported values re *daily* and *monthly*.

The additional parameters for the command are:

| Parameter | Description                                                                                                        | Example                                                                                  |
|-----------|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| -ch       | The check name. All data quality checks are described in the [Checks](../../checks/index.md) section.              | -ch=[daily_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-nulls-percent) |
| -col      | The target column name. This parameter supports patterns such as *\*_id*.                                          | -col=customer_id                                                                         |
| -ct       | The check type to run only checks of that type. Supported values are  *profiling*, *monitoring* and *partitioned*. | -ct=monitoring                                                                           |
| -ts       | The time scale for *monitoring* and *partitioned* checks. Supported values re *daily* and *monthly*.               | -ts=daily                                                                                |


## Targeting check types
The following examples will run only a subset of data quality checks, targeting checks by the check name
or by a check type.

Run the [daily_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-nulls-percent) check
on all columns of the *public.fact_sales* table.

``` { .asc .annotate }
dqo> check run -c=sales-dwh -t=public.fact_sales -ch=daily_nulls_percent(1)
```

1. The name of the data quality check to run. Other check names are listed in the [Checks](../../checks/index.md) section.

Run only profiling checks on the table and all its columns.

``` { .asc .annotate }
dqo> check run -c=sales-dwh -t=public.fact_sales -ct=profiling(1)
```

1. The check type. Other check type names are *monitoring* and *partitioned*.

Run only daily monitoring checks, without running monthly checks.

``` { .asc .annotate }
dqo> check run -c=sales-dwh -t=public.fact_sales -ct=profiling -ts=daily(1)
```

1. The *monitoring* and *partitioned* checks time scale. The supported values are *daily* and *monthly*.

