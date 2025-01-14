---
title: Column list or order changed data quality checks, SQL examples
---
# Column list or order changed data quality checks, SQL examples

A table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.


___
The **column list or order changed** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column list or order changed


**Check description**

Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_column_list_or_order_changed`</span>|Detect if the column list or order has changed|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*column_list_ordered_hash*](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile column list or order changed data quality check.

??? example "Managing profile column list or order changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_column_list_or_order_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_list_or_order_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_column_list_or_order_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_list_or_order_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_column_list_or_order_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_column_list_or_order_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_list_or_order_changed
        ```

        You can also run this check on all tables  on which the *profile_column_list_or_order_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_list_or_order_changed
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
      profile_column_list_or_order_changed:
        error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## daily column list or order changed


**Check description**

Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_column_list_or_order_changed`</span>|Detect if the column list or order has changed|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*column_list_ordered_hash*](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily column list or order changed data quality check.

??? example "Managing daily column list or order changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_column_list_or_order_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_list_or_order_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_column_list_or_order_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_list_or_order_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_column_list_or_order_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_column_list_or_order_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_list_or_order_changed
        ```

        You can also run this check on all tables  on which the *daily_column_list_or_order_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_list_or_order_changed
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
        daily_column_list_or_order_changed:
          error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## monthly column list or order changed


**Check description**

Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_column_list_or_order_changed`</span>|Detect if the column list or order has changed|[schema](../../../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*column_list_ordered_hash*](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[*value_changed*](../../../reference/rules/Comparison.md#value-changed)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly column list or order changed data quality check.

??? example "Managing monthly column list or order changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_column_list_or_order_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_list_or_order_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_column_list_or_order_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_list_or_order_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_column_list_or_order_changed* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_column_list_or_order_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_list_or_order_changed
        ```

        You can also run this check on all tables  on which the *monthly_column_list_or_order_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_list_or_order_changed
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
        monthly_column_list_or_order_changed:
          error: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
