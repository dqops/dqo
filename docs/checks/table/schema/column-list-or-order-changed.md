# column list or order changed data quality checks

Table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.


___
The **column list or order changed** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column list or order changed


**Check description**

Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_list_or_order_changed|profiling| |Consistency|[column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile column list or order changed data quality check.

??? example "Managing profile column list or order changed check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=profile_column_list_or_order_changed
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=profile_column_list_or_order_changed
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_list_or_order_changed
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-10"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    schema:
      profile_column_list_or_order_changed:
        warning: {}
        error: {}
        fatal: {}
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

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_list_or_order_changed|monitoring|daily|Consistency|[column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily column list or order changed data quality check.

??? example "Managing daily column list or order changed check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=daily_column_list_or_order_changed
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=daily_column_list_or_order_changed
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_list_or_order_changed
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-11"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      schema:
        daily_column_list_or_order_changed:
          warning: {}
          error: {}
          fatal: {}
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

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_list_or_order_changed|monitoring|monthly|Consistency|[column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)|[value_changed](../../../reference/rules/Comparison.md#value-changed)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly column list or order changed data quality check.

??? example "Managing monthly column list or order changed check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=monthly_column_list_or_order_changed
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=monthly_column_list_or_order_changed
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_list_or_order_changed
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-11"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    monthly:
      schema:
        monthly_column_list_or_order_changed:
          warning: {}
          error: {}
          fatal: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_list_ordered_hash](../../../reference/sensors/table/schema-table-sensors.md#column-list-ordered-hash)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    






___


