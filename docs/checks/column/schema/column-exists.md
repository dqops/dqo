# column exists data quality checks

Column level check that reads the metadata of the monitored table and verifies that the column still exists in the data source.
 The data quality sensor returns 1.0 when the column was found or 0.0 when the column was not found.


___
The **column exists** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column exists


**Check description**

Checks the metadata of the monitored table and verifies if the column exists.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_column_exists|profiling| |Completeness|[column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../reference/rules/Comparison.md#equals-1)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile column exists data quality check.

??? example "Managing profile column exists check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=profile_column_exists
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=profile_column_exists
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_exists
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-12"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        schema:
          profile_column_exists:
            warning: {}
            error: {}
            fatal: {}
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

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_column_exists|monitoring|daily|Completeness|[column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../reference/rules/Comparison.md#equals-1)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily column exists data quality check.

??? example "Managing daily column exists check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=daily_column_exists
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=daily_column_exists
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_exists
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-13"
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
              warning: {}
              error: {}
              fatal: {}
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

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_column_exists|monitoring|monthly|Completeness|[column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)|[equals_1](../../../reference/rules/Comparison.md#equals-1)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly column exists data quality check.

??? example "Managing monthly column exists check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=monthly_column_exists
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=monthly_column_exists
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_exists
        ```

**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-13"
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
              warning: {}
              error: {}
              fatal: {}
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_exists](../../../reference/sensors/column/schema-column-sensors.md#column-exists)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    






___


