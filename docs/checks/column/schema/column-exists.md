---
title: column exists data quality checks
---
# column exists data quality checks

A column-level check that reads the metadata of the monitored table and verifies if the column still exists in the data source.
 The data quality sensor returns a value of 1.0 when the column is found or 0.0 when the column is not found.


___
The **column exists** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column exists


**Check description**

Checks the metadata of the monitored table and verifies if the column exists.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_column_exists`</span>|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Completeness](../../../dqo-concepts/data-quality-dimensions.md#data-completeness)|[*column_exists*](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[*equals_1*](../../../reference/rules/Comparison.md#equals-1)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile column exists data quality check.

??? example "Managing profile column exists check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_column_exists --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_column_exists --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_column_exists --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_column_exists --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_column_exists* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_column_exists
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_exists
        ```

        You can also run this check on all tables (and columns)  on which the *profile_column_exists* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_column_exists
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-10"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        schema:
          profile_column_exists:
            error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## daily column exists


**Check description**

Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_column_exists`</span>|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Completeness](../../../dqo-concepts/data-quality-dimensions.md#data-completeness)|[*column_exists*](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[*equals_1*](../../../reference/rules/Comparison.md#equals-1)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily column exists data quality check.

??? example "Managing daily column exists check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_column_exists --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_column_exists --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_column_exists --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_column_exists --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_column_exists* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_column_exists
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_exists
        ```

        You can also run this check on all tables (and columns)  on which the *daily_column_exists* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_column_exists
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-11"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## monthly column exists


**Check description**

Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_column_exists`</span>|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Completeness](../../../dqo-concepts/data-quality-dimensions.md#data-completeness)|[*column_exists*](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[*equals_1*](../../../reference/rules/Comparison.md#equals-1)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly column exists data quality check.

??? example "Managing monthly column exists check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_column_exists --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_column_exists --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_column_exists --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_column_exists --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_column_exists* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_column_exists
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_exists
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_column_exists* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_column_exists
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-11"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          schema:
            monthly_column_exists:
              error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
