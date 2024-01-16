# column count changed data quality checks

A table-level check that detects if the number of columns in the table has changed since the last time the check (checkpoint) was run.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.


___
The **column count changed** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column count changed


**Check description**

Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`profile_column_count_changed`</span>|profiling| |Consistency|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile column count changed data quality check.

??? example "Managing profile column count changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=profile_column_count_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_column_count_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=profile_column_count_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_column_count_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_column_count_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_column_count_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_count_changed
        ```

        You can also run this check on all tables  on which the *profile_column_count_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=profile_column_count_changed
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-8"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    schema:
      profile_column_count_changed:
        error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## daily column count changed


**Check description**

Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`daily_column_count_changed`</span>|monitoring|daily|Consistency|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily column count changed data quality check.

??? example "Managing daily column count changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=daily_column_count_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_column_count_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=daily_column_count_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_column_count_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_column_count_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_column_count_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_count_changed
        ```

        You can also run this check on all tables  on which the *daily_column_count_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=daily_column_count_changed
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-9"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      schema:
        daily_column_count_changed:
          error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## monthly column count changed


**Check description**

Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`monthly_column_count_changed`</span>|monitoring|monthly|Consistency|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly column count changed data quality check.

??? example "Managing monthly column count changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=monthly_column_count_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_column_count_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=monthly_column_count_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_column_count_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_column_count_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_column_count_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_count_changed
        ```

        You can also run this check on all tables  on which the *monthly_column_count_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_column_count_changed
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-9"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    monthly:
      schema:
        monthly_column_count_changed:
          error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
