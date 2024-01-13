# column type changed data quality checks

Column level check that detects if the data type of the column has changed since the last time it was retrieved.
 This check calculates a hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column&#x27;s data types has changed.


___
The **column type changed** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column type changed


**Check description**

Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_type_changed|profiling| |Consistency|[column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile column type changed data quality check.

??? example "Managing profile column type changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=profile_column_type_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=profile_column_type_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=profile_column_type_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=profile_column_type_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_column_type_changed* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_column_type_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_type_changed
        ```

        You can also run this check on all tables (and columns)  on which the *profile_column_type_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -col=column_name_*-ch=profile_column_type_changed
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
          profile_column_type_changed:
            error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## daily column type changed


**Check description**

Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_type_changed|monitoring|daily|Consistency|[column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily column type changed data quality check.

??? example "Managing daily column type changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=daily_column_type_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=daily_column_type_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=daily_column_type_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=daily_column_type_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_column_type_changed* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_column_type_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_type_changed
        ```

        You can also run this check on all tables (and columns)  on which the *daily_column_type_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -col=column_name_*-ch=daily_column_type_changed
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
            daily_column_type_changed:
              error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## monthly column type changed


**Check description**

Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_type_changed|monitoring|monthly|Consistency|[column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly column type changed data quality check.

??? example "Managing monthly column type changed check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=monthly_column_type_changed --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=monthly_column_type_changed --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -col=column_name-ch=monthly_column_type_changed --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -col=column_name-ch=monthly_column_type_changed --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_column_type_changed* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_column_type_changed
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_type_changed
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_column_type_changed* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -col=column_name_*-ch=monthly_column_type_changed
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
            monthly_column_type_changed:
              error: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_type_hash](../../../reference/sensors/column/schema-column-sensors.md#column-type-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


